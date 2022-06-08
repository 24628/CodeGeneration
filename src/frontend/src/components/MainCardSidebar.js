import {Card, Col} from "react-bootstrap";


export default function MainCardSidebar(props){

    return (
        <Col xs={{span: 9, offset:3}}>
            <Card style={{ width: '100%', marginTop: "20px"}}>
                <Card.Body>
                    {props.element}
                </Card.Body>
            </Card>
        </Col>
    )
}