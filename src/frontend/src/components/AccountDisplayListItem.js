import {Badge, ListGroup, Nav, Placeholder} from "react-bootstrap";
import React from "react";
import {Link} from "react-router-dom";

export function currencyFormatter (value){
    return new Intl.NumberFormat('nl-NL', {
    style: 'currency',
    currency: 'EUR',
}).format(value);
}
export default function AccountDisplayListItem(props){



    if(props.account == null)
        return (
            <ListGroup.Item
                as="li"
                className="d-flex justify-content-between align-items-start"
            >
                <div className="ms-2 me-auto">
                    <div className="fw-bold"><Placeholder style={{ width: '150px' }}/></div>
                    <Placeholder xs={10} />
                </div>
                <h4><Badge style={{ width: '50px' }} bg="secondary" pill>_
                </Badge></h4>
            </ListGroup.Item>


        )

    return (
        <ListGroup.Item
            as="li"
            className="d-flex justify-content-between align-items-start"
        >
            <div className="ms-2 me-auto">
                <div className="fw-bold">{props.account.iban}</div>
                {props.account.type === "NORMAL" ? "Payment account" : "Savings account"}
            </div>
            <h4><Badge bg={props.account.type === "NORMAL" ? "success" : "primary"} pill>
                {currencyFormatter(props.account.balance)}
            </Badge></h4>
        </ListGroup.Item>
    )



}