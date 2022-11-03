import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/HOME/home";
import store from "./store/Store/store.js";
import { Provider } from "react-redux";
import { legacy_createStore as createStore } from "redux";
import CountReducer from "./store/Reducers/rootReducer";

const store = createStore(CountReducer);

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Provider store={store}>
            <Route path="/" element={<Home />} exact />
          </Provider>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
