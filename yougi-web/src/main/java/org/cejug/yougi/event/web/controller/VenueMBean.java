/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Hildeberto Mendonça.
 *
 * This application is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This application is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * There is a full copy of the GNU Lesser General Public License along with
 * this library. Look for the file license.txt at the root level. If you do not
 * find it, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA.
 * */
package org.cejug.yougi.event.web.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.cejug.yougi.event.business.EventBean;
import org.cejug.yougi.event.business.RoomBean;
import org.cejug.yougi.event.business.SessionBean;
import org.cejug.yougi.event.business.VenueBean;
import org.cejug.yougi.event.entity.Event;
import org.cejug.yougi.event.entity.Room;
import org.cejug.yougi.event.entity.Session;
import org.cejug.yougi.event.entity.Venue;
import org.cejug.yougi.web.controller.LocationMBean;

/**
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@ManagedBean
@RequestScoped
public class VenueMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private VenueBean venueBean;

    @EJB
    private RoomBean roomBean;

    @EJB
    private SessionBean sessionBean;

    @EJB
    private EventBean eventBean;

    @ManagedProperty(value = "#{param.id}")
    private String id;

    @ManagedProperty(value = "#{param.eventId}")
    private String eventId;

    @ManagedProperty(value="#{locationMBean}")
    private LocationMBean locationMBean;

    private Event event;

    private Venue venue;

    private List<Room> rooms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public LocationMBean getLocationMBean() {
        return locationMBean;
    }

    public void setLocationMBean(LocationMBean locationMBean) {
        this.locationMBean = locationMBean;
    }

    public List<Session> getSessions(Room room) {
        return sessionBean.findSessionsByRoom(this.event, room);
    }

    public List<Room> getRooms() {
        if(this.rooms == null) {
            this.rooms = roomBean.findRooms(this.venue);
        }
        return this.rooms;
    }

    @PostConstruct
    public void load() {
        if (this.eventId != null && !this.eventId.isEmpty()) {
            this.event = eventBean.findEvent(eventId);
        }

        if (this.id != null && !this.id.isEmpty()) {
            this.venue = venueBean.findVenue(id);
        }
    }


}