import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";
import ExchangeRateGraphComponent from "./components/exchangeRateGraph";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";


export default class Root extends Component<{}, {}> {

    render() {
        return <div>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="title" color="inherit">
                        Exchange Rate Predictor
                    </Typography>
                </Toolbar>
            </AppBar>
            <Switch>
                <Route exact path="/" component={ExchangeRateGraphComponent} />
            </Switch>
        </div>;
    }
}

