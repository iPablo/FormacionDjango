#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django.shortcuts import render, get_object_or_404

from .models import NewsItem, Event
from .forms import NewsForm, EventsForm
from django.utils import timezone


def index(request):
    return render(request, 'base/index.html')


def obtener_todo(request):
    news = NewsItem.objects.order_by('-publish_date')
    events = Event.objects.order_by('-start_date')
    contenido = {'news': news, 'events': events}
    return render(request, 'base/todos.html', contenido)


def obtener_news(request):
    news = NewsItem.objects.order_by('-publish_date')
    contenido = {'news': news}
    return render(request, 'base/news.html', contenido)


def obtener_events(request):
    events = Event.objects.order_by('-start_date')
    contenido = {'events': events}
    return render(request, 'base/events.html', contenido)


def detail_news(request, new_id):
    new = get_object_or_404(NewsItem, pk=new_id)
    return render(request, 'base/detail-news.html', {'new': new})


def detail_events(request, event_id):
    event = get_object_or_404(Event, pk=event_id)
    return render(request, 'base/detail-events.html', {'event': event})


def create_new(request):
    if request.method == "POST":
        form = NewsForm(request.POST)
        if form.is_valid():
            new = form.save(commit=False)
            new.publish_date = timezone.now()
            new.save()
            news = NewsItem.objects.all()
            return render(request, 'base/news.html', {'news': news})
    else:
        form = NewsForm()
    return render(request, 'base/create-new.html', {'form': form})


def update_new(request, new_id):
    new = get_object_or_404(NewsItem, pk=new_id)
    if request.method == "POST":
        form = NewsForm(request.POST, instance=new)
        if form.is_valid():
            post = form.save(commit=False)
            post.save()
            news = NewsItem.objects.all()
            return render(request, 'base/news.html', {'news': news})
    else:
        form = NewsForm(instance=new)
    return render(request, 'base/form-event.html', {'form': form})


def delete_new(request, new_id):
    new = get_object_or_404(NewsItem, pk=new_id)
    new.delete()
    news = NewsItem.objects.all()
    return render(request, 'base/news.html', {'news': news})


def create_event(request):
    if request.method == "POST":
        form = EventsForm(request.POST)
        if form.is_valid():
            event = form.save(commit=False)
            if event.start_date < event.end_date:
                event.save()
                events = Event.objects.all()
                return render(request, 'base/events.html', {'events': events})
            else:
                mensaje = 'La fecha de inicio no puede ser posterior a la fecha de fin'
                return render(request, 'base/error.html', {'mensaje': mensaje})
    else:
        form = EventsForm()
    return render(request, 'base/form-event.html', {'form': form})


def update_event(request, event_id):
    event = get_object_or_404(Event, pk=event_id)
    if request.method == "POST":
        form = EventsForm(request.POST, instance=event)
        if form.is_valid():
            post = form.save(commit=False)
            if post.start_date < post.end_date:
                post.save()
                events = Event.objects.all()
                return render(request, 'base/events.html', {'events': events})
            else:
                mensaje = 'La fecha de inicio no puede ser posterior a la fecha de fin'
                return render(request, 'base/error.html', {'mensaje': mensaje})
    else:
        form = EventsForm(instance=event)
    return render(request, 'base/form-event.html', {'form': form})


def delete_event(request, event_id):
    event = get_object_or_404(Event, pk=event_id)
    event.delete()
    events = Event.objects.all()
    return render(request, 'base/events.html', {'events': events})
