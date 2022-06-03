import {Button, Card, Form} from "react-bootstrap";
import React from "react";
function Login() {

    return (
        <>
            <Card.Title>Sign-in</Card.Title>
            <img src="logo.svg"/>
            <Form>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Username</Form.Label>
                    <Form.Control type="text" placeholder="Enter username" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" />
                </Form.Group>
                <Button variant="primary" style={{width: '100%'}} type="submit">
                    Submit
                </Button>
            </Form>
        </>
    )
}

export default Login;