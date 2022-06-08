import {Card, Col} from "react-bootstrap";
import {Route, Routes} from "react-router-dom";
import Login from "../pages/login";
import Home from "../pages/Home";
import React from "react";


export default function MainCardNoSide(props){

    return (
        <Col md={{span: 6, offset:3}}>
            <Card style={{ width: '100%', marginTop: "20px"}}>
                <Card.Body>
                    {props.element}
                </Card.Body>
            </Card>
        </Col>
    )
}