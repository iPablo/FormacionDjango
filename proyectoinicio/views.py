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
		fecha=request.POST['fecha']
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

		