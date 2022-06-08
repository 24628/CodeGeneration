import React, {useContext, useEffect, useState} from "react";
import AuthCheck from "../components/AuthCheck";
import SideNav from "../components/SideNav";
import {Badge, ListGroup} from "react-bootstrap";
import DataFetch from "../controller/dataFetch";
import AccountDisplayListItem, {currencyFormatter} from "../components/AccountDisplayListItem";
import {Link} from "react-router-dom";
import {accountContext} from "../App";

function Home() {

    const [accounts,setAccounts] = useState([null,null]);
    const [total,setTotal] = useState(0);
    const {setaccountC} = useContext(accountContext);
    useEffect(()=>{
        DataFetch.getAccountsById().then(val => {
            //intentional slowdown to showcase placeholders
            setTimeout(() => {
                let ttl = 0;
                val.accounts.map((item,i) => {
                    if(item != null)
                        ttl += item.balance;
                })
                setTotal(ttl);
                setAccounts(val.accounts);
            },500);
        });
    }, []);

    return(
        <>
            <AuthCheck/>
            <SideNav/>
            <h1>Welcome!</h1>
            <ListGroup as="ol">
                {accounts.map((item,index)=>{

                    return <Link key={index} onClick={()=>setaccountC(item)} to={"/account/"+item?.iban}><AccountDisplayListItem  account={item}/></Link>

                })}
                <ListGroup.Item
                    as="li"
                    className="d-flex justify-content-between align-items-start"
                    disabled
                >
                    <div className="ms-2 me-auto fw-bold">
                       Total:
                    </div>
                    <h4><Badge bg="secondary" pill>
                        {currencyFormatter(total)}
                    </Badge></h4>
                </ListGroup.Item>
            </ListGroup>
        </>
    )
}

export default Home;