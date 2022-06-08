import React, {useContext} from "react";
import {currencyFormatter} from "./AccountDisplayListItem";
import {accountContext} from "../App";
import {Navigate} from "react-router-dom";

export default function TransactionTableRow(props) {

    const {accountC} = useContext(accountContext);
    if(accountC == null)
        return (
            <Navigate to="/"/>
        );

    return (
        <tr>
            <td>{props.transaction.accountFrom === accountC.iban ? 'You' : props.transaction.accountFrom}</td>
            <td>{props.transaction.accountTo === accountC.iban ? 'You' : props.transaction.accountTo}</td>
            <td>{new Date(props.transaction.date).toLocaleString()}</td>
            <td>{currencyFormatter(props.transaction.amount)}</td>
        </tr>
    )
}