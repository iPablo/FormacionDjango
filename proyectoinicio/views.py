#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse, Http404, HttpResponseRedirect
from django.template import loader
from .models import Base, BaseNews, Event, NewsItem
from django.core.urlresolvers import reverse
from django.views import generic
from django.utils import timezone
from django.core.exceptions import ValidationError
from django.views.generic import CreateView, DeleteView, DetailView, UpdateView
import datetime
from django.contrib.messages.views import SuccessMessageMixin
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import NewsItemSerializer

def index(request):
	return render(request, 'proyectoinicio/index.html')

def dameNoticias(request):
	bloque_noticias = NewsItem.objects.order_by('-publish_date')
	contexto = {'bloque_noticias': bloque_noticias}
	return render(request, 'proyectoinicio/bloquenoticias.html', contexto)

def dameEventos(request):
	bloque_eventos =Event.objects.order_by('-start_date')
	contexto = {'bloque_eventos': bloque_eventos}
	return render(request, 'proyectoinicio/bloqueeventos.html', contexto)

def dameTodo(request):
	bloque_noticias = NewsItem.objects.order_by('-publish_date')
	bloque_eventos =Event.objects.order_by('-start_date')
	contexto={
		'bloque_eventos' : bloque_eventos ,
		'bloque_noticias' : bloque_noticias
	}
	return render(request, 'proyectoinicio/bloquetodo.html', contexto)

def vistaNoticia(request, noticia_pk):
	noticia = get_object_or_404(NewsItem, pk=noticia_pk)
	contexto={
		'noticia':noticia
	}
	return render(request, 'proyectoinicio/vistaAmpliada.html', contexto)

def borraNoticia(request, noticia_pk):
	noticia = get_object_or_404(NewsItem, pk=noticia_pk)
	noticia.delete()
	mensaje_confirmacion = "Se ha borrado la noticia con exito"
	contexto={
		'mensaje_confirmacion' : mensaje_confirmacion
	}
	return render(request, 'proyectoinicio/index.html', contexto)

def editaNoticia(request, noticia_pk):
	noticia=get_object_or_404(NewsItem, pk=noticia_pk)
	contexto={
		'noticia': noticia
	}
	return render(request, 'proyectoinicio/vistaEdicion.html', contexto)

def editar(request, noticia_pk):
	try:
		noticia=get_object_or_404(NewsItem, pk=noticia_pk)
		noticia.title=request.POST['title']
		noticia.description =request.POST['description']
		noticia.publish_date =request.POST['fecha']
		noticia.save()
		mensaje_confirmacion = "Se ha editado la noticia correctamente"
		contexto={
			'mensaje_confirmacion' : mensaje_confirmacion
		}
		return render(request, 'proyectoinicio/index.html', contexto)
	except (ValidationError, ValueError, NameError):
		mensaje_error = "Tienes algun tipo de error en la validación"
		contexto={
			'mensaje_error' : mensaje_error,
			'noticia': noticia
		}
		return render(request, 'proyectoinicio/vistaEdicion.html', contexto)

def creaNoticia(request):
	return render(request, 'proyectoinicio/creaNoticia.html')

def crear(request):
	try:
		titulo=request.POST['title']
		descripcion=request.POST['description']
		fecha=timezone.now()
		noticia = NewsItem(title=titulo, description=descripcion, publish_date=fecha)
		noticia.save()
		mensaje_confirmacion = "Se ha añadido la noticia correctamente. ¡Enhorabuena!"
		contexto={
			'mensaje_confirmacion' : mensaje_confirmacion
		}
		return render(request, 'proyectoinicio/index.html', contexto)
	except (ValidationError, ValueError, NameError):
		mensaje_error = "Tienes algun tipo de error en la validación"
		contexto={
			'mensaje_error' : mensaje_error,
		}
		return render(request, 'proyectoinicio/creaNoticia.html', contexto)

class NoticiasView(generic.ListView):
	model =  NewsItem
	template_name = 'proyectoinicio/bloquenoticias.html'
	context_object_name = 'bloque_noticias'
	def get_queryset(self):
		return NewsItem.objects.order_by('-publish_date')

class NoticiasDetalleView(generic.DetailView):
	model = NewsItem
	pk_url_kwarg ='noticia_pk'
	template_name = 'proyectoinicio/vistaAmpliada.html'
	context_object_name = 'noticia' #Estamos diciendo que el objeto que trata se llama "noticia"

class NoticiasDelete(generic.DeleteView):
	model = NewsItem
	pk_url_kwarg ='noticia_pk'
	def get_success_url(self):
		return reverse('proyectoinicio:index')

class NoticiasUpdate(SuccessMessageMixin, generic.UpdateView):
	model = NewsItem
	success_message = "Se ha actualizado correctamente"
	fields = ['title' , 'description' , 'publish_date']
	context_object_name  = 'noticia'
	pk_url_kwarg ='noticia_pk'
	#template_name= 'proyectoinicio/vistaEdicion.html' POR DEFECTO ES nombremodel_form
	def get_success_url(self):
		return reverse('proyectoinicio:index')

class NoticiasCreate(SuccessMessageMixin, generic.CreateView):
	success_message="Se ha creado correctamente"
	model=NewsItem
	template_name= "proyectoinicio/newsitem_create_form.html"
	fields = ['title' , 'description', 'publish_date']
	def get_success_url(self):
		return reverse('proyectoinicio:index')

class EventsView(generic.ListView):
	model= Event
	template_name = 'proyectoinicio/bloqueeventos.html'
	context_object_name= 'bloque_eventos'
	def get_queryset(self):
		return Event.objects.order_by('-start_date')

class EventDetail(generic.DetailView):
	model = Event
	fields = ['title', 'description' , 'start_date' , 'end_date']
	template_name="proyectoinicio/event_detail.html"

class EventCreate(SuccessMessageMixin, generic.CreateView):
	model = Event
	fields = ['title', 'description' , 'start_date' , 'end_date']
	template_name="proyectoinicio/event_create_form.html"
	success_message = "Se ha creado el evento satisfactoriamente."
	def get_success_url(self):
		return reverse('proyectoinicio:index')

class EventDelete(SuccessMessageMixin, generic.DeleteView):
	model = Event
	pk_url_kwarg ='event_pk'
	success_message = "Se ha borrado satisfactoriamente"
	def get_success_url(self):
		return reverse('proyectoinicio:index')

class EventUpdate(SuccessMessageMixin, generic.UpdateView):
	model = Event
	success_message = "Se ha actualizado satisfactoriamente"
	fields = ['title', 'description' , 'start_date' , 'end_date']
	context_object_name = "evento"
	pk_url_kwarg = "event_pk"
	def get_success_url(self):
		return reverse('proyectoinicio:index')


@api_view(['GET' , 'POST'])
def newsitem_list(request, format=None):
	"""
	Devuelve todas las noticias o las crea
	"""
	if request.method == 'GET':
		noticias = NewsItem.objects.all()
		serializador = NewsItemSerializer(noticias, many=True)
		return Response(serializador.data)

	elif request.method == 'POST':
		serializer = NewsItemSerializer(data=request.data)
		if serializer.is_valid():
			serializer.save()
			return Response(serializer.data, status=status.HTTP_201_CREATED)
		return Response(serializer.errors, status=statis.HTTP_400_BAD_REQUEST)

@api_view(['GET' , 'PUT', 'DELETE'])
def newsitem_detail(request, pk, format=None):
	"""
	Devuelve, actualiza, o borra un newsitem
	"""
	try:
		noticia = NewsItem.objects.get(pk=pk)
	except NewsItem.DoesNotExist:
		return Response(status=status.HTTP_404_NOT_FOUND)

	if request.method == 'GET':
		serializador = NewsItemSerializer(noticia)
		return Response(serializador.data)

	elif request.method == 'PUT':
		serializador = NewsItemSerializer(noticia, data=request.data)
		if serializador.is_valid():
			serializador.save()
			return JSONResponse(serializador.data)
		return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

	elif request.method == 'DELETE':
		noticia.delete()
		return Response(status=status.HTTP_204_NO_CONTENT)