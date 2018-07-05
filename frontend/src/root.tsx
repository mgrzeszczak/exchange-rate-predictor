import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";
import ExchangeRateGraphComponent from "./components/hello";

export default class Root extends Component<{}, {}> {

    render() {
        return <div>
            <Switch>
                <Route exact path="/" component={ExchangeRateGraphComponent} />
            </Switch>
        </div>;
    }
}

