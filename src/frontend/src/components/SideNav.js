import React from "react";
import {Nav} from "react-bootstrap";
import '../App.css'
import {Link} from "react-router-dom";

const SideNav = props => {


    return (
        <>

            <Nav className="col-3  d-block bg-light sidebar mt-5"
                 onSelect={selectedKey => {
                     if(selectedKey == "signout"){
                        localStorage.clear();
                        window.location.href = '/';
                     }
                 }}
            >
                <div className="sidebar-sticky"></div>
                <Nav.Item>
                    <Nav.Link to="/" as={Link}>Home</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link eventKey="signout" >
                        Sign-out
                    </Nav.Link>
                </Nav.Item>
            </Nav>

        </>
    );
};
export default SideNav
