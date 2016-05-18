# Better Scheduler

Better Scheduler is an android app to help coordinate and schedule meeting times.  Potential uses include:

  - Groups collating schedules to find times for regular meetings
  - TA's making timeslots for homework demos
  - Friends trying to find a good time for party or event

### Current vs. Ours

We're looking primarily to Doodle and WhenIsGood for what our app needs to do, and how it can be improved.  Both services can accomplish the things we've outlined, but each is better suited to certain kinds of scheduling polls and neither has a good interface on mobile.  Particularly:

  - Doodle is excellent for polls where respondants only choose one timeslot out of several available, but is much more cumbersome for selecting multiple times.  It is also prone to extremely long lists, which is awful to navigate on mobile devices.
  - WhenIsGood is functionally great, but looks terrible and is very difficult to use.  The way it allows users to 'paint' over available times makes it great for combining schedules, but it doesn't have enough restrictions to accommodate single-signup polls.

Our goals are to remedy these problems by integrating both use cases into a single user interface and tailoring it to mobile devices.

### Specific Choices

- Login: Google Account
- Drag-select: link to their GitHub here

### App Pages

- Home:
  - List of all polls user is part of
  - Search button (on menu bar?)
  - Create New Poll button (floating add like Google stuff?)
  - Account Settings?
- View Poll
  - Poll name
  - Summary data (e.g., number of respondants)
  - Who made it
  - Link to generate/edit your response
  - View results (link, or just right there?)
- Respond to poll
  - Available times for a single day, drag-selectable
  - Switch days with swipe/arrow
  - Confirm button
- Create Poll
  - Series of question pages/fragments
    - List questions here
  - Select 'proposed' times, same UI as poll responding

### Roadmap

Finished Tasks:

  - Home Page
  - Creating a Poll
  - Drag-Select works in a single activity
  - Google Sign-in on the app

Up Next:
  - Tabbed activity for multiple poll days
  - Clearing backstack when finished creating poll
  - Fetching Google account info, primarily Picture
  - Basic Google App Engine framework
  - Responding to a poll
  - Viewing poll results

Down the Road:
  - Backend authentication for fetching poll info
  - Backend code to store polls
  - Poll ID generation: generate in GAE, send back for storing in Poll.  Need some async stuff here.
  - Clear local data store on logout.

Stretch Goals:
  - Implement polls with specific dates rather than a generic week
  - Implement Doodle-like single-selct polls.
  - Other login options, like Facebook
  - Web Interface, accomplish same task, but for desktop.