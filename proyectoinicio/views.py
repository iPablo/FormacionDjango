#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.shortcuts import render, get_object_or_404
from .models import Event, NewsItem
from django.core.urlresolvers import reverse
from django.views import generic
from django.contrib.messages.views import SuccessMessageMixin
from django.contrib import messages
from rest_framework import generics
from .serializers import NewsItemSerializer, EventSerializer, UserSerializer
from django.shortcuts import redirect
from .forms import NewsItemForm, EventForm
from django.contrib.auth.models import User
from rest_framework import permissions
from .permissions import IsOwnerOrReadOnly


def index(request):
	return render(request, 'proyectoinicio/index.html')


def dameNoticias(request):
	bloque_noticias = NewsItem.objects.order_by('-publish_date')
	contexto = {'bloque_noticias': bloque_noticias}
	return render(request, 'proyectoinicio/bloquenoticias.html', contexto)


def dameTodo(request):
	bloque_noticias = NewsItem.objects.order_by('-publish_date')
	bloque_eventos = Event.objects.order_by('-start_date')
	contexto = {
		'bloque_eventos': bloque_eventos,
		'bloque_noticias': bloque_noticias
	}
	return render(request, 'proyectoinicio/bloquetodo.html', contexto)


def vistaNoticia(request, noticia_pk):
	noticia = get_object_or_404(NewsItem, pk=noticia_pk)
	contexto = {
		'noticia': noticia
	}
	return render(request, 'proyectoinicio/vistaAmpliada.html', contexto)


def borraNoticia(request, noticia_pk):
	noticia = get_object_or_404(NewsItem, pk=noticia_pk)
	noticia.delete()
	messages.add_message(request, messages.SUCCESS, 'Se ha borrado la noticia con exito', fail_silently=True)
	return redirect('proyectoinicio:index')


def editaNoticia(request, noticia_pk):
	noticia = get_object_or_404(NewsItem, pk=noticia_pk)
	form = NewsItemForm(request.POST or None, instance=noticia)
	if form.is_valid():
		form.save()
		messages.add_message(request, messages.SUCCESS, 'Se ha editado correctamente', fail_silently=True)
		return redirect('proyectoinicio:index')
	return render(request, 'proyectoinicio/vistaEdicion.html', {'form': form})


def creaNoticia(request):
	form = NewsItemForm(request.POST or None)
	if form.is_valid():
		form.save()
		messages.add_message(request, messages.SUCCESS, 'Enhorabuena, se ha creado tu noticia', fail_silently=True)
		return redirect('proyectoinicio:index')
	return render(request, 'proyectoinicio/creaNoticia.html', {'form': form})


class NoticiasView(generic.ListView):
	model = NewsItem
	template_name = 'proyectoinicio/bloquenoticias.html'
	context_object_name = 'bloque_noticias'

	def get_queryset(self):
		return NewsItem.objects.order_by('-publish_date')


class NoticiasDetalleView(generic.DetailView):
	model = NewsItem
	pk_url_kwarg = 'noticia_pk'
	template_name = 'proyectoinicio/vistaAmpliada.html'
	context_object_name = 'noticia'  # Estamos diciendo que el objeto que trata se llama "noticia"


class NoticiasDelete(generic.DeleteView):
	model = NewsItem
	pk_url_kwarg = 'noticia_pk'

	def get_success_url(self):
		return reverse('proyectoinicio:index')


class NoticiasUpdate(SuccessMessageMixin, generic.UpdateView):
	model = NewsItem
	success_message = "Se ha actualizado correctamente"
	context_object_name = 'noticia'
	pk_url_kwarg = 'noticia_pk'
	form_class = NewsItemForm
	# template_name= 'proyectoinicio/vistaEdicion.html'
	# POR DEFECTO ES nombremodel_form

	def get_success_url(self):
		return reverse('proyectoinicio:index')


class NoticiasCreate(SuccessMessageMixin, generic.CreateView):
	success_message = "Se ha creado correctamente"
	model = NewsItem
	template_name = "proyectoinicio/newsitem_create_form.html"
	form_class = NewsItemForm


	def get_success_url(self):
		return reverse('proyectoinicio:index')


class EventsView(generic.ListView):
	model = Event
	template_name = 'proyectoinicio/bloqueeventos.html'
	context_object_name = 'bloque_eventos'

	def get_queryset(self):
		return Event.objects.order_by('-start_date')


class EventDetail(generic.DetailView):
	model = Event
	template_name = "proyectoinicio/event_detail.html"
	form_class = EventForm



class EventCreate(SuccessMessageMixin, generic.CreateView):
	model = Event
	form_class = EventForm
	template_name = "proyectoinicio/event_create_form.html"
	success_message = "Se ha creado el evento satisfactoriamente."

	def get_success_url(self):
		return reverse('proyectoinicio:index')


class EventDelete(SuccessMessageMixin, generic.DeleteView):
	model = Event
	pk_url_kwarg = 'event_pk'
	success_message = "Se ha borrado satisfactoriamente"

	def get_success_url(self):
		return reverse('proyectoinicio:index')


class EventUpdate(SuccessMessageMixin, generic.UpdateView):
	model = Event
	success_message = "Se ha actualizado satisfactoriamente"
	form_class = EventForm
	context_object_name = "evento"
	pk_url_kwarg = "event_pk"

	def get_success_url(self):
		return reverse('proyectoinicio:index')


class NewsItemList(generics.ListCreateAPIView):
	"""
	Lista todas las noticias, o las crea.
	"""
	queryset = NewsItem.objects.all()
	serializer_class = NewsItemSerializer
	permission_classes = (permissions.IsAuthenticatedOrReadOnly,)

	def perform_create(self, serializer):
		serializer.save(owner=self.request.user)


class NewsItemDetail(generics.RetrieveUpdateDestroyAPIView):
	"""
	Devuelve, actualiza o borra una instancia de NewsItem
	"""
	queryset = NewsItem.objects.all()
	serializer_class = NewsItemSerializer
	permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly,)

class EventRESTList(generics.ListCreateAPIView):
	"""
	Lista todas los eventos, o los crea.
	"""
	queryset = Event.objects.all()
	serializer_class = EventSerializer
	permission_classes = (permissions.IsAuthenticatedOrReadOnly,)

	def perform_create(self, serializer):
		serializer.save(owner=self.request.user)


class EventRESTDetail(generics.RetrieveUpdateDestroyAPIView):
	"""
	Devuelve, actualiza o borra una instancia de Event
	"""
	queryset = Event.objects.all()
	serializer_class = EventSerializer
	permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly,)


class UserList(generics.ListAPIView):
	queryset = User.objects.all()
	serializer_class = UserSerializer


class UserDetail(generics.RetrieveAPIView):
	queryset = User.objects.all()
	serializer_class = UserSerializer
