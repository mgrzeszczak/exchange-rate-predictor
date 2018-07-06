import { combineReducers } from "redux";
import { getCurrencyCodesReducer } from "./getCurrencyCodesReducer";
import { getExchangeRatesReducer } from "./getExchangeRatesReducer";
import { getCurrencyCodes } from "../actions";

export interface AppState {
    currencyCodes: string[];
    exchangeRates: any[];
}

const rootReducer = combineReducers(
    {
        currencyCodes: getCurrencyCodesReducer,
        exchangeRates: getExchangeRatesReducer
    }
);

export default rootReducer;
