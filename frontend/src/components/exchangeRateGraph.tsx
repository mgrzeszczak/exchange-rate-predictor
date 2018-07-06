import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { getCurrencyCodes, getExchangeRates } from "../actions";
import { AppState } from "../reducers";
import { Line } from "react-chartjs-2";
import Button from "@material-ui/core/Button";
import CurrencySelectorComponent from "./currencySelector";
import DatePicker from "./datePicker";
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";

import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import Typography from "@material-ui/core";

class ExchangeRateGraphComponent extends Component<any, any> {

    constructor(props: any) {
        super(props);
        this.props.getCurrencyCodes();
        const today = new Date();
        const date = `${today.getFullYear()}-${this.pad(today.getMonth() + 1, 2)}-${this.pad(today.getDay() + 1, 2)}`;
        this.state = {
            dateFrom: date,
            dateTo: date,
            selectedCodes: []
        };
        this.updateSelectedCurrencies = this.updateSelectedCurrencies.bind(this);
        this.updateFrom = this.updateFrom.bind(this);
        this.updateTo = this.updateTo.bind(this);
        this.show = this.show.bind(this);
    }

    pad(num: number, size: number) {
        return ("000000000" + num).substr(-size);
    }

    componentDidUpdate() {

    }

    show() {
        console.log("pulling data");
        console.log(this.state);
        this.props.getExchangeRates(this.state.selectedCodes, this.state.dateFrom, this.state.dateTo);
    }

    updateSelectedCurrencies(selected: string[]) {
        this.setState({
            selectedCodes: selected,
            dateFrom: this.state.dateFrom,
            dateTo: this.state.dateTo
        });
    }

    updateFrom(from: string) {
        this.setState({
            dateFrom: from,
            dateTo: this.state.dateTo,
            selectedCodes: this.state.selectedCodes
        });
    }

    updateTo(to: string) {
        this.setState({
            dateTo: to,
            dateFrom: this.state.dateFrom,
            selectedCodes: this.state.selectedCodes
        });
    }

    getRandomColor() {
        const letters = "0123456789ABCDEF".split("");
        let color = "#";
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    render() {
        let labels = [];
        let datasets = [];

        console.log(this.props.exchangeRates);
        if (this.state.selectedCodes.length > 0 && this.props.exchangeRates !== undefined && this.props.exchangeRates.length > 0) {
            console.log("in if");
            const rates = this.props.exchangeRates;
            const dict: any = {};
            this.state.selectedCodes.forEach((c: string) => {
                dict[c] = rates.filter((r: any) => r.code === c);
            });

            labels = dict[this.state.selectedCodes[0]].map((v: any) => v.date);
            console.log(dict);
            datasets = this.state.selectedCodes.filter((s: string) => dict[s].length > 0).map((c: string) => {
                const color = this.getRandomColor();
                return {
                    label: dict[c][0].currency,
                    fill: false,
                    lineTension: 0.1,
                    backgroundColor: color,
                    borderColor: color,
                    borderCapStyle: "butt",
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: "miter",
                    pointBorderColor: color,
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: color,
                    pointHoverBorderColor: color,
                    pointHoverBorderWidth: 2,
                    pointRadius: 3,
                    pointHitRadius: 10,
                    data: dict[c].map((v: any) => v.value)
                };
            });

        }



        const data = {
            labels: labels,
            datasets: datasets
        };

        return <div style={{ flexGrow: 1 }}>
            <Grid container spacing={24}>
                <Grid item xs>
                    <CurrencySelectorComponent codes={this.props.currencyCodes} updateSelection={(selected: string[]) => this.updateSelectedCurrencies(selected)} />
                </Grid>
            </Grid>
            <Grid container spacing={24}>
                <Grid item xs>
                    <DatePicker onChange={(v: string) => this.updateFrom(v)} label="From" />
                </Grid>
                <Grid item xs>
                    <DatePicker onChange={(v: string) => this.updateTo(v)} label="To" />
                </Grid>
                <Grid item xs>

                    <Card style={{ margin: 5 }}>
                        <CardContent>
                            <div style={{ margin: "0 auto", width: "50%" }}>
                                <Button variant="contained" color="secondary" onClick={() => this.show()}>Update</Button>
                            </div>
                        </CardContent>
                    </Card>

                </Grid>
            </Grid>
            <Grid container spacing={24} >
                <Grid item xs>
                    <Card>
                        <CardContent>
                            <div style={{ width: 1000, margin: "0 auto" }}>
                                <Line data={data} />
                            </div>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
        </div>;
    }

}

function mapStateToProps(appState: AppState) {
    return {
        currencyCodes: appState.currencyCodes,
        exchangeRates: appState.exchangeRates
    };
}

export default connect(mapStateToProps, { getCurrencyCodes, getExchangeRates })(ExchangeRateGraphComponent);
