from django.conf.urls import url
from .views import (
        NewsItemList,
        NewsItemDetail,
        NewsItemCreation,
        NewsItemUpdate,
        NewsItemDelete,
        EventList,
        EventDetail,
        EventCreation,
        EventUpdate,
        EventDelete
    )
from . import views
app_name = 'blog'
urlpatterns = [
        url(r'^$', views.index, name='index'),
        url(r'^todo/$', views.todo, name='todo'),
        url(r'^noticias/$', views.newsitem_list, name='noticias'),
        url(r'^noticias/$', NewsItemList.as_view(), name='listnews'),
        url(r'^noticias/(?P<pk>[0-9]+)/$', NewsItemDetail.as_view(), name='detailnews'),
        url(r'^noticias/v2/nuevo$', NewsItemCreation.as_view(), name='newnews'),
        url(r'^noticias/v2/(?P<pk>[0-9]+)/editar/$', NewsItemUpdate.as_view(), name='editnews'),
        url(r'^noticias/v2/borrar/(?P<pk>[0-9]+)/$', NewsItemDelete.as_view(), name='deletenews'),
        url(r'^eventos/$', views.event_list, name='eventos'),
        url(r'^eventos/$', EventList.as_view(), name='listevent'),
        url(r'^eventos/(?P<pk>[0-9]+)/$', EventDetail.as_view(), name='detailevent'),
        url(r'^eventos/nuevo$', EventCreation.as_view(), name='newevent'),
        url(r'^eventos/editar/(?P<pk>[0-9]+)/$', EventUpdate.as_view(), name='editevent'),
        url(r'^eventos/borrar/(?P<pk>[0-9]+)/$', EventDelete.as_view(), name='deleteevent'),
#noticias v1
        url(r'^noticias/$', views.newsitem_list, name='noticias'),
        url(r'^noticias/v1/nuevo$', views.newsitem_create, name='1newnews'),
        url(r'^noticias/v1/(?P<pk>[0-9]+)/editar$', views.newsitem_update, name='v1editnews'),
        url(r'^noticias/v1/(?P<pk>[0-9]+)/borrar$', views.newsitem_delete, name='v1deletenews'),
]
