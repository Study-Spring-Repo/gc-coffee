import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Link, Route, Switch, Redirect, useParams } from 'react-router-dom';
import { useLayoutEffect, useState } from 'react';
import { Container, Form, Table, Button } from 'react-bootstrap';
import axios from 'axios';

function CustomerList() {
  const [customers, setCustomers] = useState([]);

  useLayoutEffect(() => {
    axios.get("http://localhost:8080/kdt/api/v1/customers", {
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(v => {
        setCustomers(v.data);
      })
  }, []);

  return (
    <>
      <h1>Customer Table</h1>
      <Table striped bordered hover>
        <thead>
        <tr>
          <th>id</th>
          <th>name</th>
          <th>email</th>
          <th>createdAt</th>
          <th>lastLoginAt</th>
        </tr>
        </thead>
        <tbody>
        {customers.map(v =>
          <tr key={v.customerId}>
            <td><Link to={`/customers/${v.customerId}`}>{v.customerId}</Link></td>
            <td>{v.name}</td>
            <td>{v.email}</td>
            <td>{v.createdAt}</td>
            <td>{v.lastLoginAt}</td>
          </tr>,
        )}
        </tbody>
      </Table>
    </>);
}

function Customer() {
  const [customer, setCustomer] = useState({});
  let { customerId } = useParams();
  useLayoutEffect(() => {
    axios.get("http://localhost:8080/kdt/api/v1/customers/" + customerId)
      .then(v => {
        console.log(v);
        setCustomer(v.data);
      })
  }, []);

  const submit = data => {
    axios.post("http://localhost:8080/kdt/api/v1/customers/" + customerId, data)
      .then(v => {
        setCustomer(v.data);
      })
  }

  return (
    <Form>
      <h1>Customer Details</h1>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Customer ID</Form.Label>
        <Form.Control type="email" value={customer.customerId} readOnly={true} />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Email address</Form.Label>
        <Form.Control type="email" value={customer.email} />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Name</Form.Label>
        <Form.Control type="text" value={customer.name} readOnly={true} />
      </Form.Group>

      <Button onClick={(e) => submit(customer)}>Submit</Button>
    </Form>
  );
}

function App() {
  return (
    <Container>
      <Router>
        <Switch>
          <Route path='/customers/:customerId'>
            <Customer />
          </Route>
          <Route path='/customers'>
            <CustomerList />
          </Route>
          <Route path='/'>
            <Redirect to="/customers" />
          </Route>
        </Switch>
      </Router>
    </Container>
  );
}

export default App;
