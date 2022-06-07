import {Card, Col} from "react-bootstrap";
import {Route, Routes} from "react-router-dom";
import Login from "../pages/login";
import Home from "../pages/Home";
import React from "react";


export default function MainCardSidebar(props){

    return (
        <Col xs={{span: 9, offset:3}}>
            <Card style={{ width: '100%', marginTop: "20px"}}>
                <Card.Body>
                    {props.element}
                </Card.Body>
            </Card>
        </Col>
    )
}