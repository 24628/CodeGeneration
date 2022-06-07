import axios from "axios";
const baseurl = "http://localhost:8080/";
class DataFetch{
    init(){
        axios.defaults.headers.post['Content-Type'] ='application/json;charset=utf-8';
        axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
        const token = localStorage.getItem("token");
        if (token) {
            setAuthToken(token);
        }
    }

    static login(username,password){
        return new Promise((resolve, reject) => {
            axios.post(baseurl+"login", {
                "password": password,
                "username": username
            })
                .then(response => {
                    const token  =  response.data.token;
                    localStorage.setItem("token", token);
                    localStorage.setItem("role",response.data.userEntity.role);
                    localStorage.setItem("uuid",response.data.userEntity.userId);
                    setAuthToken(token);
                    resolve({status: true});
                })
                .catch(err => {
                    reject({status: false,message: getErrorHandled(err)});
                });
        })

    }

    static getAccountsById(limit){
        return new Promise((resolve, reject) => {
            axios.get(baseurl+"accounts/id/"+localStorage.getItem("uuid"), limit)
                .then(response => {
                    resolve({status: true,accounts: response.data.accountEntityList});
                })
                .catch(err => {
                    reject({status: false,message: getErrorHandled(err)});
                });
        })
    }

    static getTransactions(limit){
        return new Promise((resolve, reject) => {
            axios.get(baseurl+"transactions", limit)
                .then(response => {
                    resolve(response.data.transactionEntityList);
                })
                .catch(err => {
                    reject({error: true,message: getErrorHandled(err)});
                });
        })
    }
    static createTransactions(from,to,amount){
        return new Promise((resolve, reject) => {
            axios.post(baseurl+"transactions", {from: from, to: to, amount: amount})
                .then(response => {
                    resolve(response);
                })
                .catch(err => {
                    reject({error: true,message: getErrorHandled(err)});
                });
        })
    }
}
function  getErrorHandled(err){
    console.log(err);
    // if(err.status === 500){
    //
    // }
    return err.response.data.message;
}

export const setAuthToken = token => {
    if (token) {
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    }
    else
        delete axios.defaults.headers.common["Authorization"];
}

export default DataFetch;