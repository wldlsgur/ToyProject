import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";

function App() {
  return (
    <>
      <Router>
        <Switch>
          <Route path="/main" component={} />
          <Route path="/profile" component={} />
          <Route path="/" component={} />
          <Route render={() => <div className="error">에러 페이지</div>} />
        </Switch>
      </Router>
    </>
  );
}

export default App;
