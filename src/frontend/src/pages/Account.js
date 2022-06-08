import React, {useEffect, useState} from "react";
import AuthCheck from "../components/AuthCheck";
import SideNav from "../components/SideNav";
import {Link, useParams} from "react-router-dom";
import {Button, ButtonGroup, Table} from "react-bootstrap";
import DataFetch from "../controller/dataFetch";
import TransactionTableRow from "../components/TransactionTableRow";

function Account() {
    const iban = useParams()['iban'];
    const [transactions,setTransactions] = useState([]);
    const [page,setPage] = useState(0);
    useEffect(()=>{
        DataFetch.getTransactions(iban,{limit: 10,offset: page*10}).then(val => {
            setTransactions(val)
        });
    }, [page]);

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
            <ButtonGroup aria-label="Basic example">
                <Button onClick={() => setPage(Math.max(page-1,0))} variant="secondary">Previous</Button>
                <Button onClick={() => setPage(page+1)} variant="secondary">Next</Button>
            </ButtonGroup>
        </>
    )
}

export default Account;