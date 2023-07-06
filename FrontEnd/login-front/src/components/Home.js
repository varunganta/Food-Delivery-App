import React, {useState, useEffect} from 'react';
import axios from 'axios';

export default function Home() {
    const [users, setUsers] = useState([])
    useEffect(()=>{
        loadUsers();
    }, []);

    const loadUsers = async() => {
        const result = await axios.get("http://localhost:8080/api/v1/registration");
        setUsers(result.data);
    }

    return (
        <div className = "container">
            <div className = 'py-4'>
                <table className="table border shadow">
                  <thead>
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">FirstName</th>
                      <th scope="col">LastName</th>
                      <th scope="col">Handle</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                        users.map((user,index) =>(
                            <tr>
                              <th scope="row" key = {index}>{index+1}</th>
                              <td>{user.name}</td>
                              <td>{user.username}</td>
                              <td>{user.email}</td>
                            </tr>
                        ))
                    }
                  </tbody>
                </table>
            </div>
        </div>

    )
}
