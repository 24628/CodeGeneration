import React from "react";
import {currencyFormatter} from "./AccountDisplayListItem";

export default function TransactionTableRow(props) {

    return (
        <tr>
            <td>{props.transaction.accountFrom === localStorage.getItem("uuid") ? 'You' : props.transaction.accountFrom}</td>
            <td>{props.transaction.accountTo === localStorage.getItem("uuid") ? 'You' : props.transaction.accountTo}</td>
            <td>{new Date(props.transaction.date).toLocaleString()}</td>
            <td>{currencyFormatter(props.transaction.amount)}</td>
        </tr>
    )
}