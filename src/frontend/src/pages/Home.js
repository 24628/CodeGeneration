import React, {useEffect} from "react";
import AuthCheck from "../components/AuthCheck";
import SideNav from "../components/SideNav";
import {Badge, ListGroup} from "react-bootstrap";
import DataFetch from "../controller/dataFetch";

function Home() {
    useEffect(()=>{
        DataFetch.accounts().then(val => {

        });
    }, []);

    return(
        <>
            <AuthCheck/>
            <SideNav/>
            <h1>Welcome!</h1>
            <ListGroup as="ol">
                <ListGroup.Item
                    as="li"
                    className="d-flex justify-content-between align-items-start"
                >
                    <div className="ms-2 me-auto">
                        <div className="fw-bold">IBAN nummer</div>
                        Betaalrekening
                    </div>
                    <h4><Badge bg="success" pill>
                        € 15.674,67
                    </Badge></h4>
                </ListGroup.Item>
                <ListGroup.Item
                    as="li"
                    className="d-flex justify-content-between align-items-start"
                >
                    <div className="ms-2 me-auto">
                        <div className="fw-bold">Subheading</div>
                        Cras justo odio
                    </div>
                    <Badge bg="primary" pill>
                        14
                    </Badge>
                </ListGroup.Item>
                <ListGroup.Item
                    as="li"
                    className="d-flex justify-content-between align-items-start"
                    disabled
                >
                    <div className="ms-2 me-auto fw-bold">
                       Totaal:
                    </div>
                    <h4><Badge bg="secondary" pill>
                        € 15.674,67
                    </Badge></h4>
                </ListGroup.Item>
            </ListGroup>
        </>
    )
}

export default Home;