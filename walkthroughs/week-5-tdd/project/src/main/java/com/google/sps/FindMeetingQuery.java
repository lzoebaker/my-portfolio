// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.lang.Math;

/** given a collection of existing meetings (with time ranges and attendees) and a request for 
  * a meeting (with duration and mandatory/optional attendees), return the times when the meeting 
  * could happen that day.
**/
public final class FindMeetingQuery {
    private static final TimeRange START_OF_DAY_TIMERANGE =
        TimeRange.fromStartDuration(TimeRange.START_OF_DAY, 0);
    private static final TimeRange END_OF_DAY_TIMERANGE =
        TimeRange.fromStartDuration(TimeRange.END_OF_DAY + 1, 0);

  /** returns time ranges that will accomodate meeting request
   *  if no time ranges exist all (mandatory + optional) attendees can attend, time ranges that
   *  accomodate only mandatory attendees is returned instead if no mandatory time ranges exists 
   *  that mandatory attendees can attend, an empty collection is returned. 
  **/
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<TimeRange> allAttendeeTimeRanges = extractAttendingTimeRangesFromEvents(
        events, request, /* include optional attendees= */ true);
    Collection<TimeRange> acceptableOptionalTimeRanges = getConflictFreeTimeRanges(
        allAttendeeTimeRanges, request);

    if (request.getAttendees().isEmpty() || !acceptableOptionalTimeRanges.isEmpty()) {
      return acceptableOptionalTimeRanges;
    } else {
      Collection<TimeRange> mandatoryAttendeeTimeRanges = extractAttendingTimeRangesFromEvents(
          events, request, /* includes optional attendees= */ false);
      Collection<TimeRange> acceptableMandatoryTimeRanges = getConflictFreeTimeRanges(
          mandatoryAttendeeTimeRanges, request);
      return acceptableMandatoryTimeRanges;
    }
  }
  
  /** given a collection of TimeRanges and a meeting request, returns 
    * a collection of timeranges that the meeting can be scheduled in 
    * without conflicts
  **/
  private Collection<TimeRange> getConflictFreeTimeRanges(
      Collection<TimeRange> timeRanges, MeetingRequest request) {
        
    // edge case: if meeting request duration is too long, return empty list
    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
      return Arrays.asList();
    }

    // edge case: if there are no exisiting meetings, return entire day
    if (timeRanges.isEmpty()) {
      return Arrays.asList(TimeRange.WHOLE_DAY);
    }
    
    List<TimeRange> timeRangesByStart = prepareAndSortTimeRanges(timeRanges);
    
    List<TimeRange> acceptableTimeRanges = new ArrayList<TimeRange>();
    TimeRange currentMeetingTimeRange;
    TimeRange nextMeetingTimeRange;

    for (int timeRangeIndex= 0; timeRangeIndex< timeRangesByStart.size() - 1; timeRangeIndex++) {
      currentMeetingTimeRange = timeRangesByStart.get(timeRangeIndex);  
      nextMeetingTimeRange = timeRangesByStart.get(timeRangeIndex + 1);
      // if overlap, merge with next meeting
      if (currentMeetingTimeRange.overlaps(nextMeetingTimeRange)) {
        timeRangesByStart.set(
           timeRangeIndex+ 1, getMergedMeetingTimeRange(currentMeetingTimeRange, nextMeetingTimeRange));
      } else if (canGapFitRequest(currentMeetingTimeRange.end(), nextMeetingTimeRange.start(), request)) { 
        TimeRange accetableRange = TimeRange.fromStartEnd(
            currentMeetingTimeRange.end(), nextMeetingTimeRange.start(), 
            /* inclusive of end= */ false);
        acceptableTimeRanges.add(accetableRange);
      }
    }
    return acceptableTimeRanges;
  }

  /* Creates a collection of TimeRanges from the associated collection of Events */
  private Collection<TimeRange> extractAttendingTimeRangesFromEvents(
      Collection<Event> events, MeetingRequest request, boolean includeOptionalAttendees) {   
    Collection<TimeRange> timeRanges = new ArrayList<TimeRange>();
    Collection<String> requestedAttendees = new HashSet<String>();
    requestedAttendees.addAll(request.getAttendees());
    
    if (includeOptionalAttendees) {
      requestedAttendees.addAll(request.getOptionalAttendees());
    } 

    for (Event event : events) {
      // only add events if they contain attendees that are requested for this meeting
      if (!Collections.disjoint(requestedAttendees, event.getAttendees())) {
        timeRanges.add(event.getWhen());
      }
    }
    return timeRanges;
  }
  
  /* Returns true if a gap between two time periods can fit in a meeting's duration*/
  private boolean canGapFitRequest(
      int earlierMeetingEnd, int latertimeRangeStart, MeetingRequest request){
    return latertimeRangeStart - earlierMeetingEnd >= request.getDuration();
  }

  private TimeRange getMergedMeetingTimeRange(TimeRange meeting1, TimeRange meeting2) {
    int newStart = Math.min(meeting1.start(), meeting2.start());
    int newEnd = Math.max(meeting1.end(), meeting2.end());
    return TimeRange.fromStartEnd(newStart, newEnd, /* inclusive of end= */ false);
  }

  private List<TimeRange> prepareAndSortTimeRanges(Collection<TimeRange> timeRanges) {
    // add dummy events at start of day and end of day with no duration 
    timeRanges.add(START_OF_DAY_TIMERANGE);
    timeRanges.add(END_OF_DAY_TIMERANGE);
    
    List<TimeRange> timeRangesByStart = new ArrayList(timeRanges);
    Collections.sort(timeRangesByStart, TimeRange.ORDER_BY_START);
    return timeRangesByStart;
  }
}
