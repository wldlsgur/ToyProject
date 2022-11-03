import { INIT_COUNT_STATUS } from "../status/count";
import { INCREMENT, DECREMENT } from "../actions/count";
import { combineReducers } from "redux";

function counter(state = INIT_COUNT_STATUS, action) {
  switch (action.type) {
    case INCREMENT:
      return {
        ...state,
        value: state.value + 1,
      };
    case DECREMENT:
      return {
        ...state,
        value: state.value - 1,
      };
    default:
      return state;
  }
}

const counterReducer = combineReducers({ counter });

export default counterReducer;
