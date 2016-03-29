from django.conf.urls import url

from . import views

app_name="proyectoinicio"
urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^v1/$', views.dameNoticias, name='noticias'),
    url(r'^eventos/$', views.EventsView.as_view(), name='eventos'),
    url(r'^todo/$', views.dameTodo, name='todo'),
    url(r'^v1/ampliar/(?P<noticia_pk>[0-9]+)$', views.vistaNoticia, name='ampliar'),
    url(r'^v1/borrar/(?P<noticia_pk>[0-9]+)$', views.borraNoticia, name='borrar'),
    url(r'^v1/editar/(?P<noticia_pk>[0-9]+)$', views.editaNoticia, name='editar'),
    url(r'^v1/confirmar/(?P<noticia_pk>[0-9]+)$', views.editar, name='confirmar'),
    url(r'^v1/crear/$', views.creaNoticia, name='crearNoticia'),
    url(r'^v1/creada/$', views.crear, name='crear'),
    url(r'^v2/$', views.NoticiasView.as_view(), name='noticiasVBC'),
    url(r'^v2/ampliar/(?P<noticia_pk>[0-9]+)$', views.NoticiasDetalleView.as_view(), name='ampliarVBC'),
    url(r'^v2/ampliar/borrar/(?P<noticia_pk>[0-9]+)$', views.NoticiasDelete.as_view(), name='borrarVBC'),
    url(r'^v2/ampliar/editar/(?P<noticia_pk>[0-9]+)$', views.NoticiasUpdate.as_view(), name='editarVBC'),
    url(r'^v2/crear/$', views.NoticiasCreate.as_view(), name='crearVBC'),
    url(r'^eventos/(?P<pk>[0-9]+)$', views.EventDetail.as_view(), name='ampliareventos'),
    url(r'^eventos/crear$', views.EventCreate.as_view(), name='crearEvento'),
    url(r'^eventos/borrar/(?P<event_pk>[0-9]+)$', views.EventDelete.as_view(), name='borrarEvento'),
    url(r'^eventos/editar/(?P<event_pk>[0-9]+)$', views.EventUpdate.as_view(), name='editarEvento'),

]

