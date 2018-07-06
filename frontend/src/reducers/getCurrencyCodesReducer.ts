import { ActionType } from "../actions";

export function getCurrencyCodesReducer(state: string[] = [], action: any): any {
    switch (action.type) {
        case ActionType.GET_CURRENCY_CODES:
            return action.payload.data;
        default:
            return state;
    }
}
