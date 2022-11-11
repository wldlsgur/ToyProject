import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/HOME/home";

// const store = createStore(CountReducer);

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} exact />
          <Route
            path="/hi"
            element={
              <div>
                <h4>hi</h4>
              </div>
            }
            exact
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
