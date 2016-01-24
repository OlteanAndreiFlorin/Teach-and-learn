package ro.sci.group2.dao.inmemory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.joda.time.Interval;
import org.joda.time.ReadableInterval;
import org.springframework.util.StringUtils;

import ro.sci.group2.dao.MeetingDAO;
import ro.sci.group2.domain.Meeting;
import ro.sci.group2.domain.Teacher;

public class IMMeetingDAO extends IMBaseDAO<Meeting> implements MeetingDAO {

	@Override
	public Collection<Meeting> searchByTeacher(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}
		Collection<Meeting> all = new LinkedList<>(getAll());
		for(Iterator<Meeting> it=all.iterator();it.hasNext();){
			Teacher te = it.next().getTeacher();
			String ss = te.getFirstName()+" "+te.getLastName();
			if(!ss.contains(query)){
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Collection<Meeting> searchByCity(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}
		Collection<Meeting> all=new LinkedList<>(getAll());
		for(Iterator<Meeting>it=all.iterator();it.hasNext();){
			Meeting mt=it.next();
			String ss=mt.getCity();
			if(!ss.contains(query)){
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Collection<Meeting> searchByDate(Interval interval) {
		Collection<Meeting> all=new LinkedList<>(getAll());
		for(Iterator<Meeting>it=all.iterator();it.hasNext();){
			Meeting mt =it.next();
			ReadableInterval in = mt.getMeetingInterval();
			if(!interval.overlaps(in)){
				it.remove();
			}
		}
		return all;
	}

}
