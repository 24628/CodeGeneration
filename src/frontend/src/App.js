import logo from './logo.svg';
import './App.css';
import {Card, Container, Navbar} from "react-bootstrap";
import {BrowserRouter as Router,Routes ,Route} from 'react-router-dom'
import Login from "./pages/login";
import Home from "./pages/Home";

function App() {
  return (
      <Router>
        <div className="App">
            <Navbar bg="dark" variant="dark">
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

          <Container>
              <Card style={{ width: '100%', marginTop: "20px"}}>
                  <Card.Body>
                      <Routes>
                          <Route path="/login" element={<Login/>}/>
                          <Route path="/" element={<Home/>}/>
                      </Routes>
                  </Card.Body>
              </Card>
          </Container>
        </div>
      </Router>
  );
}

export default App;
