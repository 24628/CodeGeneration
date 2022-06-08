import {Button, Card, Col, Form} from "react-bootstrap";
import React, {useState} from "react";
import DataFetch from "../controller/dataFetch";
import { useForm } from "react-hook-form";
import {Navigate} from "react-router-dom";
import {userContext} from "../App";



function Login(){
    const { register, formState: { errors }, handleSubmit } = useForm();
    const [error, setError] = useState('');
    const [login,setLogin] = useState(false);
    const { userC,setUserC } = React.useContext(userContext)

    function submit(e) {
        DataFetch.login(e.username, e.password)
            .then(r => {
                //forward to signed in page
                setUserC(r.data);
                setLogin(true);
            }).catch(reason => {
                //show error
            setError(reason.message);
            });
    }

        return (
            <>
                {login ? <Navigate to="/" /> : ''}

                    <Card.Title>Sign-in</Card.Title>
                    <img width="300px" src="logo.svg"/>
                    <Form onSubmit={handleSubmit(submit)}>
                        <label style={{color: "red"}}>{error}</label>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Username</Form.Label>
                            <Form.Control {...register("username")} type="text" placeholder="Enter username" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control {...register("password")} type="password" placeholder="Password" />
                        </Form.Group>
                        <Button variant="primary" style={{width: '100%'}} type="submit">
                            Submit
                        </Button>
                    </Form>

            </>
        )

}

export default Login;