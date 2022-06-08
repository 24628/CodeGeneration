import React, {useEffect, useState} from "react";
import AuthCheck from "../components/AuthCheck";
import SideNav from "../components/SideNav";
import {Badge, Button, Card, Form, ListGroup} from "react-bootstrap";
import DataFetch from "../controller/dataFetch";
import AccountDisplayListItem, {currencyFormatter} from "../components/AccountDisplayListItem";
import {Link, Navigate} from "react-router-dom";
import {useForm} from "react-hook-form";

function Transaction() {

    const { register, handleSubmit } = useForm();
    const [error, setError] = useState('');
    function submit(e) {
        DataFetch.createTransactions(e.from, e.to,e.amount)
            .then(r => {
                console.log(r)
            }).catch(reason => {
            setError(reason.message);
        });
    }

    return (
        <>
            <SideNav/>
            <Card.Title>Transaction</Card.Title>
            <Form onSubmit={handleSubmit(submit)}>
                <label style={{color: "red"}}>{error}</label>
                <Form.Group className="mb-3">
                    <Form.Label>From</Form.Label>
                    <Form.Control {...register("from")} type="text" placeholder="IBAN" />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>To</Form.Label>
                    <Form.Control {...register("to")} type="text" placeholder="IBAN" />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Amount</Form.Label>
                    <Form.Control {...register("amount")} type="currency" placeholder="00.00" />
                </Form.Group>
                <Button variant="primary" style={{width: '100%'}} type="submit">
                    Transer ->
                </Button>
            </Form>

        </>
    )

}

export default Transaction;