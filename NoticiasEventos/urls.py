from django.conf.urls import include, url
from . import views
from NoticiasEventos.views import v2List, v2Create, v2Update, v2Delete, EventList, EventCreate, EventUpdate, EventDelete

app_name = "NoticiasEventos" 
urlpatterns = [
	#V1 NewsItem
	url(r'^$', views.index, name="index"),
	url(r'^v1/$', views.v1List, name="v1List"),
	url(r'^v1/Create/$', views.v1Create, name="v1Create"),
	url(r'^v1/Update/(?P<pk>[0-9]+)/$', views.v1Update, name="v1Update"),
	url(r'^v1/Delete/(?P<pk>[0-9]+)/$', views.v1Delete, name="v1Delete"),

	#V2 NewsItem
	url(r'^v2/$', v2List.as_view(), name="v2List"),
	url(r'^v2/Create/$', v2Create.as_view(), name='v2Create'),
	url(r'^v2/Update/(?P<pk>[0-9]+)/$', v2Update.as_view(), name="v2Update"),
	url(r'^v2/Delete/(?P<pk>[0-9]+)/$', v2Delete.as_view(), name="v2Delete"),

	#Events
	url(r'^event/$', EventList.as_view(), name="eventList"),
	url(r'^event/Create/$', EventCreate.as_view(), name='eventCreate'),
	url(r'^event/Update/(?P<pk>[0-9]+)/$', EventUpdate.as_view(), name="eventUpdate"),
	url(r'^event/Delete/(?P<pk>[0-9]+)/$', EventDelete.as_view(), name="eventDelete"),
]