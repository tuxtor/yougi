
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
package org.cejug.yougi.event.business;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.cejug.yougi.event.entity.Event;
import org.cejug.yougi.event.entity.SponsorshipEvent;
import org.cejug.yougi.partnership.entity.Partner;
import org.cejug.yougi.entity.EntitySupport;

/**
 * This class implements the business logic of event sponsorship.
 *
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@Stateless
@LocalBean
public class SponsorshipEventBean {

    @PersistenceContext
    private EntityManager em;

    public SponsorshipEvent findSponsorshipEvent(String id) {
        if (id != null) {
            return em.find(SponsorshipEvent.class, id);
        } else {
            return null;
        }
    }

    public List<SponsorshipEvent> findSponsorshipsEvent(Event event) {
        return em.createQuery("select se from SponsorshipEvent se where se.event = :event order by se.partner.name asc").setParameter("event", event).getResultList();
    }

    public List<SponsorshipEvent> findSponsorshipsEvents(Partner sponsor) {
        return em.createQuery("select se from SponsorshipEvent se where se.partner = :sponsor order by se.event.name asc").setParameter("sponsor", sponsor).getResultList();
    }

    public void save(SponsorshipEvent sponsorshipEvent) {
        if (EntitySupport.INSTANCE.isIdNotValid(sponsorshipEvent)) {
            sponsorshipEvent.setId(EntitySupport.INSTANCE.generateEntityId());
            em.persist(sponsorshipEvent);
        } else {
            em.merge(sponsorshipEvent);
        }
    }

    public void remove(String id) {
        SponsorshipEvent eventSponsor = em.find(SponsorshipEvent.class, id);
        if (eventSponsor != null) {
            em.remove(eventSponsor);
        }
    }
}