import { ActionType } from "../actions";

interface ExchangeRate {
    id: number;
    value: number;
    currency: string;
    code: string;
    date: string;
}

export function getExchangeRatesReducer(state: ExchangeRate[] = [], action: any): ExchangeRate[] {
    switch (action.type) {
        case ActionType.GET_EXCHANGE_RATES:
            return action.payload.data;
        default:
            return state;
    }
}
