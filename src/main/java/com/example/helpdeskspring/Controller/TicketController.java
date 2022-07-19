package com.example.helpdeskspring.Controller;


import com.example.helpdeskspring.Model.Ticket;
import com.example.helpdeskspring.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping("/list")
    public ResponseEntity<Ticket> getAllTicket(){
        return new ResponseEntity(ticketService.getAllTicket(), HttpStatus.OK) ;
    }

    @GetMapping("{id}")
    Optional<Ticket> getTicketById(@PathVariable("id")long id){
        return ticketService.getTicketById(id);
    }

    @PostMapping(path="/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket createTicket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }

    @PutMapping(path="/assignee/{ticket}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Ticket> addAssignee(@PathVariable("ticket")long ticket, @PathVariable("id")long id)throws Exception{
        return ResponseEntity.ok().body(ticketService.addAssignee(ticket, id));
    }

    @PutMapping(path="/watcher/{ticket}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Ticket> addWatcher(@PathVariable("ticket")long ticket, @PathVariable("id")long id){
        return ResponseEntity.ok().body(ticketService.addWatcher(ticket, id));
    }

    @PutMapping(path="/update/{ticket}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Ticket> updateTicket(@PathVariable(value="ticket") long ticket, @RequestBody Ticket ticket1) throws Exception{
        return ResponseEntity.ok().body(ticketService.updateTicket(ticket, ticket1));
    }

    @DeleteMapping(path="/delete/{ticket}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable(value="ticket")long ticket){
        ticketService.deleteTicket(ticket);
    }



}
