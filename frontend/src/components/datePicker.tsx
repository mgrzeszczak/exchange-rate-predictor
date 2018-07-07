import TextField from "@material-ui/core/TextField";
import React, { Component, ChangeEvent } from "react";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import Typography from "@material-ui/core";

interface Props {
    onChange: (date: string) => void;
    label: string;
}

export default class DatePicker extends Component<Props, any> {

    constructor(props: any) {
        super(props);
        const today = new Date();
        const date = `${today.getFullYear()}-${this.pad(today.getMonth() + 1, 2)}-${this.pad(today.getDay() + 1, 2)}`;
        this.state = {
            date
        };
        this.props.onChange(date);
    }

    pad(num: number, size: number) {
        return ("000000000" + num).substr(-size);
    }

    onChange(e: ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        this.setState({ date: value });

        this.props.onChange(value);
    }

    render() {
        return <Card style={{ margin: 5 }}>
            <CardContent>
                <form noValidate style={{
                    width: "50%",
                    margin: "0 auto"
                }}>
                    <TextField
                        onChange={(e: ChangeEvent<HTMLInputElement>) => this.onChange(e)}
                        id="date"
                        label={this.props.label}
                        type="date"
                        value={this.state.date}
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </form >
            </CardContent>
        </Card>;
    }

}
