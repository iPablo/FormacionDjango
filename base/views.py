#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django.shortcuts import render, get_object_or_404, redirect

from .models import NewsItem, Event
from .forms import NewsForm, EventsForm
from django.views.generic import *
from django.core.urlresolvers import reverse, reverse_lazy
from base.serializers import NewsItemSerializer, EventSerializer
from rest_framework import generics

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
            new.save()
            return redirect('base:obtener_news')
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
            return redirect('base:obtener_news')
    else:
        form = NewsForm(instance=new)
    return render(request, 'base/form-event.html', {'form': form})


def delete_new(request, new_id, template_name="base/borrar.html"):
    item = get_object_or_404(NewsItem, pk=new_id)
    if request.method == 'POST':
        item.delete()
        return redirect("base:obtener_news")
    return render(request, template_name, {'item': item})


def create_event(request):
    if request.method == "POST":
        form = EventsForm(request.POST)
        if form.is_valid():
            event = form.save(commit=False)
            if event.start_date <= event.end_date:
                event.save()
                return redirect('base:obtener_events')
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
            if post.start_date <= post.end_date:
                post.save()
                return redirect('base:obtener_events')
            else:
                mensaje = 'La fecha de inicio no puede ser posterior a la fecha de fin'
                return render(request, 'base/error.html', {'mensaje': mensaje})
    else:
        form = EventsForm(instance=event)
    return render(request, 'base/form-event.html', {'form': form})


def delete_event(request, event_id, template_name="base/borrar.html"):
    item = get_object_or_404(Event, pk=event_id)
    if request.method == 'POST':
        item.delete()
        return redirect("base:obtener_events")
    return render(request, template_name, {'item': item})


"""Clases"""


class TodosList(ListView):
    model = NewsItem
    template_name = "base/todos2.html"

    def get_context_data(self, **kwargs):
        # Call the base implementation first to get a context
        context = super(TodosList, self).get_context_data(**kwargs)
        # Add in a QuerySet of all the books
        context['event_list'] = Event.objects.all()
        return context


class NewsItemList(ListView):
    model = NewsItem
    template_name = "base/news2.html"


class NewsItemDetail(DetailView):
    model = NewsItem
    template_name = "base/detalle-news-clases.html"


class EventList(ListView):
    model = Event
    template_name = "base/events2.html"


class EventsDetail(DetailView):
    model = Event
    template_name = "base/detalle-events-clases.html"


class NewsCreate(CreateView):
    model = NewsItem
    form_class = NewsForm
    template_name = "base/create-new.html"

    def get_success_url(self):
        return reverse('base:NewsItemList')


class EventsCreate(CreateView):
    model = Event
    form_class = EventsForm
    template_name = "base/form-event.html"

    def get_success_url(self):
        return reverse('base:EventList')


class NewsUpdate(UpdateView):
    model = NewsItem
    form_class = NewsForm
    template_name = "base/create-new.html"

    def get_success_url(self):
        return reverse('base:NewsItemList')


class EventsUpdate(UpdateView):
    model = Event
    form_class = EventsForm
    template_name = "base/form-event.html"

    def get_success_url(self):
        return reverse('base:EventList')


class NewsDelete(DeleteView):
    model = NewsItem
    success_url = reverse_lazy('base:NewsItemList')


class EventsDelete(DeleteView):
    model = Event
    success_url = reverse_lazy('base:EventList')

"""API"""


class NewsItemListAPI(generics.ListCreateAPIView):
    queryset = NewsItem.objects.all()
    serializer_class = NewsItemSerializer


class NewsItemDetailAPI(generics.RetrieveUpdateDestroyAPIView):
    queryset = NewsItem.objects.all()
    serializer_class = NewsItemSerializer


class EventsListAPI(generics.ListCreateAPIView):
    queryset = Event.objects.all()
    serializer_class = EventSerializer


class EventsDetailAPI(generics.RetrieveUpdateDestroyAPIView):
    queryset = Event.objects.all()
    serializer_class = EventSerializer
