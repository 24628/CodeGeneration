import logo from './logo.svg';
import './App.css';
import {Card, Col, Container, Navbar} from "react-bootstrap";
import {BrowserRouter as Router,Routes ,Route} from 'react-router-dom'
import Login from "./pages/login";
import Home from "./pages/Home";
import DataFetch, {setAuthToken} from "./controller/dataFetch";
import SideNav from "./components/SideNav";
import React, {createContext} from "react";
import MainCardSidebar from "./components/MainCardSidebar";
import MainCardNoSide from "./components/MainCardNoSide";
import 'bootstrap/dist/css/bootstrap.min.css';
import Account from "./pages/Account";
import Transaction from "./pages/Transaction";

export const userContext = createContext(0);
export const accountContext = createContext(0);

function App() {
    new DataFetch().init(); // initiate datafetch

    const [userC, setUserC] = React.useState(null);
    const [accountC, setaccountC] = React.useState(null)


    return (
      <Router>


        <div className="App">
            <Navbar style={{zIndex:"150"}} bg="dark" variant="dark">
              <Container>
                <Navbar.Brand href="/home">
                  <img
                      alt=""
                      src="/logo.svg"
                      width="30"
                      height="30"
                      className="d-inline-block align-top"
                  />{' '}
                  Paymentino
                </Navbar.Brand>
              </Container>
            </Navbar>

          <Container fluid={true}>
              <userContext.Provider value={{ userC, setUserC }}>
                  <accountContext.Provider value={{ accountC, setaccountC }}>
                      <Routes>
                          <Route path="/login" element={<MainCardNoSide element={<Login/>}/>}/>
                          <Route path="/account/:iban" element={<MainCardSidebar element={<Account/>}/>}/>
                          <Route path="/transaction" element={<MainCardSidebar element={<Transaction/>}/>}/>
                          <Route path="/" element={<MainCardSidebar element={<Home/>}/>}/>
                      </Routes>
                  </accountContext.Provider>
              </userContext.Provider>
          </Container>
        </div>
      </Router>
  );
}

export default App;
