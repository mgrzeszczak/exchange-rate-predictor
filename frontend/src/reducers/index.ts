import { combineReducers } from "redux";
import { getCurrencyCodesReducer } from "./getCurrencyCodesReducer";
import { getExchangeRatesReducer } from "./getExchangeRatesReducer";
import { PredictionData, predictExchangeRatesReducer } from "./predictExchangeRatesReducer";
import { getCurrencyCodes } from "../actions";

export interface AppState {
    currencyCodes: string[];
    exchangeRates: any[];
    predictedRates: PredictionData[];
}

const rootReducer = combineReducers(
    {
        currencyCodes: getCurrencyCodesReducer,
        exchangeRates: getExchangeRatesReducer,
        predictedRates: predictExchangeRatesReducer
    }
);

export default rootReducer;
