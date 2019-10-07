

Singletons:
Profile: the logged in user info
UserUnique: has info about a user to display
Feed: has a feed to display, can be anybody's feed (profile or another user)
Story: has a story to display
Following: has following users to display (anybody's followings, the profile or a user)
Followers: has followers to display

Each singleton (other than UserUnique and Profile) has an owner.
When the recycler views retrieve the next page of data they look at the owner of the singleton to
show to know what to ask to the database.

The ContentFragment (and other fragments using a recycler) holds the main view that contains the feed, story, following and followers
It tells what the recycler what to show. StatusRecyclerFragment shows either the content
in the feed singleton or story singleton.
The FollowRecycler either show the content of Following or Followers singletons.

Fragments or activities using ContentFragment (or using a recycler) set singleton ownerships
They tell the feed, story, following and followers which user they belong to.
