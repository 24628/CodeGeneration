import React, {useEffect, useState} from "react";
import AuthCheck from "../components/AuthCheck";
import SideNav from "../components/SideNav";
import {Link, useParams} from "react-router-dom";
import {Button, Table} from "react-bootstrap";
import DataFetch from "../controller/dataFetch";
import AccountDisplayListItem from "../components/AccountDisplayListItem";
import TransactionTableRow from "../components/TransactionTableRow";

function Account() {
    const iban = useParams()['iban'];
    const [transactions,setTransactions] = useState([]);
    useEffect(()=>{
        DataFetch.getTransactions().then(val => {
            setTransactions(val)
        });
    }, []);

    return(
        <>
            <AuthCheck/>
            <SideNav/>
            <h1 >{iban}</h1>
            <Link to="/transaction"><Button variant="success">Transfer money</Button></Link>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>date</th>
                    <th>amount</th>
                </tr>
                </thead>
                <tbody>
                {transactions.map((item,index)=>{
                    return <TransactionTableRow key={index} transaction={item}/>
                })}
                </tbody>
            </Table>
        </>
    )
}

export default Account;