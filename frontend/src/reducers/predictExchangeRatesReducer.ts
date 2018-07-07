import { ActionType } from "../actions";
import { ExchangeRate } from "./getExchangeRatesReducer";

export interface PredictionData {
    code: string;
    real: ExchangeRate[];
    predicted: ExchangeRate[];
}

export function predictExchangeRatesReducer(state: PredictionData[] = [], action: any): any {
    switch (action.type) {
        case ActionType.PREDICT_EXCHANGE_RATES:
            return action.payload.data;
        default:
            return state;
    }
}
