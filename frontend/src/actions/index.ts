import axios, { AxiosPromise } from "axios";

export const ACTION_TYPE = "ACTION_TYPE";

const host = "";

export enum ActionType {
    GET_CURRENCY_CODES,
    GET_EXCHANGE_RATES,
    PREDICT_EXCHANGE_RATES
}

export function getCurrencyCodes(): any {
    return {
        type: ActionType.GET_CURRENCY_CODES,
        payload: axios.get(`${host}/api/exchange-rate/codes`)
    };
}

export function getExchangeRates(codes: string[], from: string, to: string): any {
    const joined = codes.join(",");
    return {
        type: ActionType.GET_EXCHANGE_RATES,
        payload: axios.get(`${host}/api/exchange-rate/${joined}/${from}/${to}`)
    };
}

export function predictExchangeRates(codes: string[], count: number): any {
    const joined = codes.join(",");
    return {
        type: ActionType.PREDICT_EXCHANGE_RATES,
        payload: axios.get(`${host}/api/exchange-rate/predict/${joined}/${count}`)
    };
}
