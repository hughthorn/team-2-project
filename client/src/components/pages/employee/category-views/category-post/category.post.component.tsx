import React, { useState, useEffect } from 'react';
import './category.post.component.css';
import { Tickets } from '../../../../../models/Tickets';
import { Replies } from '../../../../../models/Replies';
import { Card, Modal, Button, ButtonGroup } from 'react-bootstrap';
import * as employeeRemote from '../../../../../remote/employee.remote';

interface CategoryPostComponentProps {
    setView: (str: 'CATEGORY_POST' | 'CATEGORY_PENDING' | 'CATEGORY_ACCEPTED' | 'CATEGORY_RESOLVED' | 'ALL') => void;
}

// Test objects if data is needed
const testTicketsPost: Tickets[] = [{ 
    ticketId: 1,
    title: 'title',
    datePosted: '12-12-12-12-12-12',
    dateResolved: '12-12-12-12-12-12',
    userFirstName: 'first',
    userLastName: 'last',
    img: undefined, //!implement img storage
    message: 'message',
    ticketStatus: 0,
    adminId: 1
}];

const testRepliesPost : Replies[] = [{
    rid: 1,
    ticketPostId: 1,
    timestamp: 'a date',
    userId: 1,
    replies: 'jdfalk;sjdfkal;sfdjl;ksdafj;lksad'
}]

export const CategoryPostComponent: React.FC<CategoryPostComponentProps> = (props) => {

    // All tickets from Global Model
    const [allTickets, setAllTickets] = useState<Tickets[]>([]);

    // All replies from Global Model
    const [allReplies, setAllReplies] = useState<Replies[]>([]);

    // Modal to see post
    const [modalVisible, setModalVisible] = useState(false);

    // Get ticket by id
    const [ticketById, setTicketById] = useState<Tickets[]>([]);

    // const renderPostCategoryComponent = () => {
    //     return props.tickets.map(ticket => {
    //         return (<CategoryPostComponent key={ticket.ticketStatus} ticket={ticket} />)
    //     })
    // }

    useEffect(() => {
        loadPosts();
    }, []);

        /**Load ticket-card data */ 
        const loadPosts = () => {  
            employeeRemote.getRepliesById().then(replies => {
                setAllReplies(replies);
            });
    
            employeeRemote.getTicketByPostCategory(0).then(tickets => {
                setAllTickets(tickets);
            });
        };
    
        /**View Ticket Button */
        const loadModal = (a: any)=> {
            setTicketById(a); //load modal with ticket data
            setModalVisible(true); //Open Modal
        };
    
    return (
        <div>
            <section>
                <ButtonGroup aria-label="Basic example">
                    <Button variant="secondary" onClick={() => props.setView('ALL')}>All</Button>
                    <Button variant="secondary" onClick={() => props.setView('CATEGORY_POST')}>Post</Button>
                    <Button variant="secondary" onClick={() => props.setView('CATEGORY_PENDING')}>Pending</Button>
                    <Button variant="secondary" onClick={() => props.setView('CATEGORY_ACCEPTED')}>Accepted</Button>
                    <Button variant="secondary" onClick={() => props.setView('CATEGORY_RESOLVED')}>Resolved</Button>
                </ButtonGroup>
                <table>
                    <thead>
                        <tr>
                            <th scope="col"># ID: </th>
                            <th scope="col">Post: </th>
                            <th scope="col">Request Date: </th>
                            <th scope="col">Resolved Date: </th>
                            <th scope="col">Status: </th>
                        </tr>
                    </thead>
                    <tbody>
                        {testTicketsPost.map(a => {
                            return (
                            <tr key={a.ticketId}>
                                <th scope="row">{a.ticketId}</th>
                                <td>{a.title}</td>
                                <td>{typeof a.datePosted == 'string' ? a.datePosted : a.datePosted.toDateString()}</td>
                                <td>{typeof a.dateResolved == 'string' ? a.dateResolved : a.dateResolved.toDateString()}</td>
                                <td>{a.ticketStatus}</td>  
                                <button className="btn btn-success"
                                    onClick={() => loadModal(a)}>
                                    View Ticket/Post
                                </button>
                            </tr>
                            )
                        })}
                    </tbody>
                </table>
            </section>
            <section>
                <Modal show={modalVisible} onHide={() => setModalVisible(false)}  >
                    <Modal.Header>
                        <Modal.Title>
                            Ticket/Post Entry
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <table>
                            <thead>
                                <tr>
                                    <th scope="col"># ID: </th>
                                    <th scope="col">Post: </th>
                                    <th scope="col">Request Date: </th>
                                    <th scope="col">Date Resolved: </th>
                                    <th scope="col">Status: </th>
                                </tr>
                            </thead>
                            <tbody>
                                {testTicketsPost.map(a => {
                                    return (
                                    <tr key={a.ticketId}>
                                        <th scope="row">{a.ticketId}</th>
                                        <td>{a.title}</td>
                                        <td>{typeof a.datePosted == 'string' ? a.datePosted : a.datePosted.toDateString()}</td>
                                        <td>{typeof a.dateResolved == 'string' ? a.dateResolved : a.dateResolved.toDateString()}</td>
                                        <td>{a.ticketStatus}</td>
                                    </tr>
                                    )
                                })}      
                            </tbody>
                        </table>

                        <Modal.Footer>
                            <Button onClick={() => setModalVisible(false)}>Close</Button>
                        </Modal.Footer>
                    </Modal.Body>
                </Modal>
            </section>    
        </div>
    )   
}