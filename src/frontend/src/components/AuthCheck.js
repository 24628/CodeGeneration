import {Navigate, useLocation} from 'react-router-dom'
import React from "react";
import {setAuthToken} from "../controller/dataFetch";

const customerPaths = ['/'];

export default function AuthCheck(){
    let path = useLocation().pathname;
    let authorised = false;
    if(localStorage.getItem("token") == null){
        authorised = false;
    }else{

        if (localStorage.getItem("role") === "CUSTOMER" && customerPaths.includes(path))
            authorised = true;

    }

    if(!authorised)
        return (<Navigate to="/login" />);
}