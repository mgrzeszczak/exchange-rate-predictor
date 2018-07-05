import axios, { AxiosPromise } from "axios";

export const ACTION_TYPE = "ACTION_TYPE";

const host = "http://localhost:8090";

export enum ActionType {
    GET_CURRENCY_CODES,
    GET_EXCHANGE_RATE
}

export function getCurrencyCodes(): any {
    return {
        type: ActionType.GET_CURRENCY_CODES,
        payload: axios.get(`${host}/api/exchange-rate/codes`)
    };
}
