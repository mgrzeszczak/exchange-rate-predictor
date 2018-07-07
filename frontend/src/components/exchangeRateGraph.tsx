import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { getCurrencyCodes, getExchangeRates, predictExchangeRates } from "../actions";
import { AppState } from "../reducers";
import { Line } from "react-chartjs-2";
import Button from "@material-ui/core/Button";
import CurrencySelectorComponent from "./currencySelector";
import DatePicker from "./datePicker";
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import { PredictionData } from "../reducers/predictExchangeRatesReducer";
import { ExchangeRate } from "../reducers/getExchangeRatesReducer";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import Typography from "@material-ui/core";
import colors from "../utils/colors";

class ExchangeRateGraphComponent extends Component<any, any> {

    constructor(props: any) {
        super(props);
        this.props.getCurrencyCodes();
        const today = new Date();
        const date = `${today.getFullYear()}-${this.pad(today.getMonth() + 1, 2)}-${this.pad(today.getDay() + 1, 2)}`;
        this.state = {
            dateFrom: date,
            dateTo: date,
            selectedCodes: [],
            showPredictions: false
        };
        this.updateSelectedCurrencies = this.updateSelectedCurrencies.bind(this);
        this.updateFrom = this.updateFrom.bind(this);
        this.updateTo = this.updateTo.bind(this);
        this.show = this.show.bind(this);
        this.showPredictions = this.showPredictions.bind(this);
    }

    pad(num: number, size: number): string {
        return ("000000000" + num).substr(-size);
    }

    showPredictions(): void {
        this.props.predictExchangeRates(this.state.selectedCodes, 7);
        if (!this.state.showPredictions) {
            this.togglePredictions();
        }
    }

    togglePredictions(): void {
        this.setState({
            dateFrom: this.state.dateFrom,
            dateTo: this.state.dateTo,
            selectedCodes: this.state.selectedCodes,
            showPredictions: !this.state.showPredictions
        });
    }

    show(): void {
        this.props.getExchangeRates(this.state.selectedCodes, this.state.dateFrom, this.state.dateTo);
        if (this.state.showPredictions) {
            this.togglePredictions();
        }
    }

    updateSelectedCurrencies(selected: string[]): void {
        this.setState({
            selectedCodes: selected,
            dateFrom: this.state.dateFrom,
            dateTo: this.state.dateTo
        });
    }

    updateFrom(from: string): void {
        this.setState({
            dateFrom: from,
            dateTo: this.state.dateTo,
            selectedCodes: this.state.selectedCodes
        });
    }

    updateTo(to: string): void {
        this.setState({
            dateTo: to,
            dateFrom: this.state.dateFrom,
            selectedCodes: this.state.selectedCodes
        });
    }

    predictedRenderData(): any {
        let labels: string[] = [];
        let datasets: any[] = [];
        if (this.props.predictedRates !== undefined && this.props.predictedRates.length > 0) {
            this.props.predictedRates.forEach((r: PredictionData, i: number) => {
                labels = r.real.concat(r.predicted).map((r: ExchangeRate) => r.date);
                const realColor = colors[i * 2];
                const predictedColor = colors[i * 2 + 1];
                const currency = r.real[0].currency;
                const realValues = r.real.map((v: ExchangeRate) => v.value);
                const predictedValues = r.predicted.map((v: ExchangeRate) => v.value);

                const realArr = new Array<number>(realValues.length + predictedValues.length);
                realValues.forEach((v: number, i: number) => realArr[i] = v);

                const predArr = new Array<number>(realValues.length + predictedValues.length);
                predictedValues.forEach((v: number, i: number) => predArr[i + realValues.length] = v);
                predArr[realValues.length - 1] = realValues[realValues.length - 1];

                datasets.push(this.createDataset(currency, realColor, realArr));
                datasets.push(this.createDataset(`predicted ${currency}`, predictedColor, predArr));
            });
        }

        const data = {
            labels: labels,
            datasets: datasets
        };

        return data;
    }

    createDataset(currency: string, color: string, values: number[]): any {
        return {
            label: currency,
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
            data: values
        };
    }

    actualRenderData(): any {
        let labels: string[] = [];
        let datasets: any = [];
        if (this.state.selectedCodes.length > 0
            && this.props.exchangeRates !== undefined
            && this.props.exchangeRates.length > 0) {
            const rates = this.props.exchangeRates;
            const dict: { [key: string]: ExchangeRate[] } = {};
            this.state.selectedCodes.forEach((c: string) => {
                dict[c] = rates.filter((r: ExchangeRate) => r.code === c);
            });

            labels = dict[this.state.selectedCodes[0]].map((v: ExchangeRate) => v.date);
            datasets = this.state.selectedCodes.filter((s: string) => dict[s].length > 0).map((c: string, i: number) => {
                const color = colors[i];
                return this.createDataset(dict[c][0].currency, color, dict[c].map((v: ExchangeRate) => v.value));
            });

        }

        const data = {
            labels: labels,
            datasets: datasets
        };

        return data;
    }

    render(): any {
        const data = this.state.showPredictions ? this.predictedRenderData() : this.actualRenderData();

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
                                <Button variant="contained" color="secondary" onClick={() => this.show()}>Plot</Button>
                                <Button variant="contained" color="secondary" onClick={() => this.showPredictions()}>Predict</Button>
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
    console.log(appState);
    return {
        currencyCodes: appState.currencyCodes,
        exchangeRates: appState.exchangeRates,
        predictedRates: appState.predictedRates
    };
}

export default connect(mapStateToProps, { getCurrencyCodes, getExchangeRates, predictExchangeRates })(ExchangeRateGraphComponent);
