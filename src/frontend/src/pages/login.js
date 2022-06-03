import {Button, Card, Form} from "react-bootstrap";
import React from "react";
class Login extends React.Component{

    constructor(props){
        super(props);
        this.state = {
            username: '', password: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    handleSubmit(e) {
        console.log( this.state);
        e.preventDefault();
    }
    render(){
        return (
            <>
                <Card.Title>Sign-in</Card.Title>
                <img src="logo.svg"/>
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label>Username</Form.Label>
                        <Form.Control onChange={this.handleChange} type="text" name='username' placeholder="Enter username" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control onChange={this.handleChange} type="password" name='password' placeholder="Password" />
                    </Form.Group>
                    <Button variant="primary" style={{width: '100%'}} type="submit">
                        Submit
                    </Button>
                </Form>
            </>
        )
    }
}

export default Login;