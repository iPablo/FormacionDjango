#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django.http import HttpResponse
from django.shortcuts import render

from .models import NewsItem


def index(request):
    objects = NewsItem.objects.order_by('-publish_date')
    context = {'objects': objects}
    return render(request, 'news/index.html', context)
