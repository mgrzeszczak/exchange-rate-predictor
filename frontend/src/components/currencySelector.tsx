import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { getCurrencyCodes } from "../actions";
import { AppState } from "../reducers";
import { Line } from "react-chartjs-2";

import FormGroup from "@material-ui/core/FormGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import Typography from "@material-ui/core";

interface Props {
    codes: string[];
    updateSelection: (selected: string[]) => void;
}

class CurrencySelectorComponent extends Component<Props, any> {

    constructor(props: any) {
        super(props);
        this.state = {
            checked: this.props.codes.map((code: string) => false)
        };
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidUpdate() {
        if (this.state.checked.length !== this.props.codes.length) {
            this.setState({
                checked: this.props.codes.map((code: string) => false)
            });
        }

    }

    handleChange(index: number) {
        const checked = this.state.checked.map((v: boolean) => v);
        checked[index] = !checked[index];
        this.setState({
            checked
        });
        this.props.updateSelection(this.props.codes.filter((code: string, index: number) => checked[index] === true));
    }

    render() {
        return <Card style={{ margin: 0 }}>
            {/* <CardHeader>
                <Typography component="h">
                    This impressive paella is a perfect party dish and a fun meal to cook together with
                    your guests. Add 1 cup of frozen peas along with the mussels, if you like.
                </Typography>
            </CardHeader> */}
            <CardContent>
                <FormGroup row>
                    {this.props.codes.map((code: string, index: number) =>
                        <FormControlLabel
                            key={index}
                            control={<Checkbox
                                checked={this.state.checked[index]}
                                onChange={() => this.handleChange(index)}
                            />}
                            label={code} />)}
                </FormGroup>
            </CardContent>
        </Card>;
    }

}

function mapStateToProps(appState: AppState) {
    return {

    };
}

export default connect(mapStateToProps, {})(CurrencySelectorComponent);


