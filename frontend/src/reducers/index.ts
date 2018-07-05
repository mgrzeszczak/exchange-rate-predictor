import { combineReducers } from "redux";
import { getCurrencyCodesReducer } from "./getCurrencyCodesReducer";
import { getCurrencyCodes } from "../actions";

export interface AppState {
    currencyCodes: string[];
}

const rootReducer = combineReducers(
    { currencyCodes: getCurrencyCodesReducer }
);

export default rootReducer;
